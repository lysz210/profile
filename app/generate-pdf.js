const { rm, mkdir, readFile } = require('fs/promises')
const path = require('path')

const sass = require('sass')
const ejs = require('ejs')
const puppeteer = require('puppeteer')
const { load } = require('js-yaml')
const { glob } = require('glob')
const _ = require('lodash')

const OUTPUT_DIR = 'pdf'
const TEMPLATES_DIR = 'templates'
const TEMPLATE_FILE = 'cv.ejs'
const STYLE_FILE = 'styles/main_style.scss'
const I18N_DIR = 'i18n'
const I18N_MESSAGES_DIR = 'assets'
const I18N_DATA_DIR = 'data'
const I18N_MESSAGES_FILE = 'messages.yaml'

async function loadYaml(filename) {
    return load(await readFile(filename))
}

async function loadAllYaml(...globPattern) {
    const filenames = await glob(path.resolve(...globPattern, '**/*.yaml'))
    return Promise.all(
        filenames.map(loadYaml)
    )
}

async function generateData(dataDir) {
    const profileDir = path.resolve(dataDir, 'me')
    const knowledgeDir = path.resolve(profileDir, 'knowledge')
    const me = await loadYaml(path.resolve(profileDir, 'index.yaml'))
    const socials = _.sortBy(
        await loadAllYaml(dataDir, '../en/me/social-accounts'),
        'ord'
    )
    socials.push(
        {
            name: 'Skype',
            username: 'lysz210'
        },
        {
            name: 'Curriculum online',
            url: 'https://lysz210.name',
            username: 'Curriculum vitae online'
        }
    )
    const works = _.reverse(_.sortBy(
        await loadAllYaml(profileDir, 'work-experiences'),
        "from"
    ))
    const educations = _.reverse(_.sortBy(
        await loadAllYaml(knowledgeDir, 'education'),
        'from'
    ))
    const languages = await loadYaml(path.resolve(knowledgeDir, 'languages.yaml'))
    const skills = _.sortBy(
        await loadAllYaml(knowledgeDir, 'it-skills'),
        'ord'
    )
    return {
        me,
        socials,
        works,
        educations,
        languages,
        skills
    }
}

async function main() {

    const outputDir = path.resolve(OUTPUT_DIR)
    const appDir = path.resolve(__dirname)
    const i18nMessageDir = path.resolve(appDir, I18N_DIR, I18N_MESSAGES_DIR)
    const i18nDataDir = path.resolve(appDir, I18N_DIR, I18N_DATA_DIR)
    const templatesDir = path.resolve(appDir, TEMPLATES_DIR)

    await rm(outputDir, { recursive: true, force: true })

    const { css } = sass.compile(path.resolve(appDir, STYLE_FILE))
    
    const browser = await puppeteer.launch({
        headless: true,
        args: [
            '--no-sandbox',
            '--disable-setuid-sandbox'
        ],
    });
    const page = await browser.newPage()
    for (const locale of ['en', 'it']) {
        const localeOutputDir = path.resolve(outputDir, locale)
        await mkdir(localeOutputDir, { recursive: true })
        const messagesFile = path.resolve(i18nMessageDir, locale, I18N_MESSAGES_FILE)
        const localeDataDir = path.resolve(i18nDataDir, locale)
        const messages = await loadYaml(messagesFile)

        const profile = await generateData(localeDataDir)
        const svgLogo = await readFile(path.resolve(appDir, 'images/europass-inline.svg'))
        const logoEuropass = `data:image/svg+xml;base64,${svgLogo.toString('base64')}`
        const html = await ejs.renderFile(
            path.resolve(templatesDir, TEMPLATE_FILE),
            {
                messages,
                profile,
                logoEuropass
            },
            {
                root: templatesDir
            }
        )
        await page.setContent(html, {waitUntil: 'networkidle0'})
        await page.addStyleTag({ content: css })
        await page.emulateMediaType('print')
        await page.pdf({
            format: 'A4',
            margin: {
                top: '0.5in',
                right: '0.5in',
                bottom: '0.5in',
                left: '0.5in'
            },
            path: path.resolve(localeOutputDir, 'cv.pdf')
        })
    }
    await browser.close()

}

main()
