let mix = require('laravel-mix');
const { Component } = require('laravel-mix/src/components/Component');
const FileCollection = require('laravel-mix/src/FileCollection');
const File = require('laravel-mix/src/File')
const Task = require('laravel-mix/src/tasks/Task');
const { load } = require('js-yaml')

class YamlToJsonTask extends Task {
    /**
     * Run the task
     */
    async run() {
        const {from: src, to} = this.data
        this.files = new FileCollection(src)

        const sourceFiles = await this.files.normalizeSourceFiles(this.files.files)
        
        for (const file of sourceFiles) {
            const files = await file.listContentsAsync({});
            for (const source of files) {
                if (source.extension() !== '.yaml') {
                    continue;
                }
                const destination = to.append(source.pathFromPublic(file.filePath).replace(/\.ya?ml$/, '.json'))
                const data = load(source.read())
                destination.makeDirectories()
                destination.write(data)
            }
        }
        this.assets = await to.listContentsAsync({})
    }
}
class YamlToJsonPlugin extends Component {
    name() {
        return 'yamlToJson'
    }
    register(from, to) {
        this.context.addTask(new YamlToJsonTask({from, to: new File(to)}))
    }
}

mix.extend('yamlToJson', YamlToJsonPlugin)

mix
.setPublicPath('dist')
.webpackConfig({
    output: {
        clean: true
    },
})
.copyDirectory('./app/i18n/data/', 'dist/i18n')
.yamlToJson('./app/i18n/data', 'dist/i18n')
.copyDirectory('./pdf/', 'dist/i18n')
;