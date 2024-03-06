let mix = require('laravel-mix');
const puppeteer = require('puppeteer')
const { readdirSync, writeFileSync } = require('fs')
const path = require('path');
const File = require('laravel-mix/src/File');
// plugins
require('laravel-mix-ejs')

mix
    .webpackConfig({
        output: {
            clean: true
        }
    })
    .extend('pdf', {
        webpackPlugins () {
            return {
                apply (compiler) {
                    compiler.hooks.done.tapPromise('Pdf', async () => {
                        const browser = await puppeteer.launch({headless: true});
                        const page = await browser.newPage()
                        const uri = `file://${path.resolve(__dirname, 'dist-static', 'cv.html')}`
                        console.log(uri)
                        await page.goto(uri, {waitUntil: 'networkidle0'})
                        const pdf = await page.pdf({ format: 'A4' })
                        writeFileSync(path.resolve(__dirname, 'dist/i18n', 'cv.pdf'), pdf)

                        await browser.close()
                    })
                }
            }
        }
    })
    .copyDirectory('./app/i18n/data/', 'dist/i18n')
    .setPublicPath('dist')
    .pdf();