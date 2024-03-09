let mix = require('laravel-mix');

mix
.setPublicPath('dist')
.webpackConfig({
    output: {
        clean: true
    }
})
.copyDirectory('./app/i18n/data/', 'dist/i18n')
.copyDirectory('./pdf/', 'dist/i18n')
;