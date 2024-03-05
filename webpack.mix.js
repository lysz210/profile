let mix = require('laravel-mix');

// plugins
require('laravel-mix-clean');
require('laravel-mix-ejs')

mix
    .clean()
    .copyDirectory('./app/i18n/data/', 'dist/i18n')
    .ejs(
        'app/templates',
        'static',
        {},
        { base: 'app/templates' }
    )
    .setPublicPath('dist');