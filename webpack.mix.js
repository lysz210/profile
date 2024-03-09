let mix = require('laravel-mix');
// plugins
require('laravel-mix-ejs')

mix
    .webpackConfig({
        output: {
            clean: true
        }
    })
    .copyDirectory('./app/i18n/data/', 'dist/i18n')
    .copyDirectory('./pdf/', 'dist/i18n')
    .setPublicPath('dist')
;