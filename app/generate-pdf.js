const { writeFileSync } = require('fs')
const { rm, mkdir } = require('fs/promises')
const path = require('path')
const sass = require('sass')
const ejs = require('ejs')
const puppeteer = require('puppeteer')

const OUTPUT_DIR = 'pdf'
const TEMPLATE_FILE = 'templates/cv.ejs'
const STYLE_FILE = 'styles/main_style.scss'

async function main() {

    const outputDir = path.resolve(OUTPUT_DIR)
    const appDir = path.resolve(__dirname)

    await rm(outputDir, { recursive: true, force: true })

    const { css } = sass.compile(path.resolve(appDir, STYLE_FILE))
    
    const browser = await puppeteer.launch({headless: true});
    const page = await browser.newPage()
    for (const locale of ['en', 'it']) {
        const localeOutputDir = path.resolve(outputDir, locale)
        await mkdir(localeOutputDir, { recursive: true })
        const html = await ejs.renderFile(
            path.resolve(appDir, TEMPLATE_FILE)
        )
        await page.setContent(html, {waitUntil: 'networkidle0'})
        await page.addStyleTag({ content: css })
        const pdf = await page.pdf({ format: 'A4' })
        writeFileSync(path.resolve(localeOutputDir, 'cv.pdf'), pdf)
    }
    await browser.close()

}

main()