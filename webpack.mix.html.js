const mix = require('laravel-mix');

// plugins
require('laravel-mix-ejs')

const OUTPUT_DIR = 'dist-static'

mix
    .webpackConfig({
        output: {
            clean: true
        }
    })
    .ejs(
        'app/templates',
        OUTPUT_DIR,
        {},
        { base: 'app/templates' }
    )
    .setPublicPath(OUTPUT_DIR);