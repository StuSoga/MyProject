/**
 * Created by zero on 15/12/26.
 */
var assign = require('lodash.assign'),
    watchify = require('watchify'),
    gulp = require("gulp"),
    gutil = require('gulp-util'),
    browserify = require('browserify'),
    reactify = require('reactify'),
    buffer = require('vinyl-buffer'),
    source = require('vinyl-source-stream'),
    sourcemaps = require('gulp-sourcemaps');

// 在这里添加自定义 browserify 选项
var customOpts = {
    entries: ['./src/index.js'],
    debug: true
};
var opts = assign({}, watchify.args, customOpts);
var b = watchify(browserify(opts));

// 在这里加入变换操作
b.transform(reactify);

gulp.task('bundle', bundle); // 这样你就可以运行 `gulp js` 来编译文件了
b.on('update', bundle); // 当任何依赖发生改变的时候，运行打包工具
b.on('log', gutil.log); // 输出编译日志到终端

function bundle() {
    return b.bundle()
        // 如果有错误发生，记录这些错误
        .on('error', gutil.log.bind(gutil, 'Browserify Error'))
        .pipe(source('bundle.js'))
        // 可选项，如果你不需要缓存文件内容，就删除
        .pipe(buffer())
        // 可选项，如果你不需要 sourcemaps，就删除
        .pipe(sourcemaps.init({loadMaps: true})) // 从 browserify 文件载入 map
        // 在这里将变换操作加入管道
        .pipe(sourcemaps.write('./')) // 写入 .map 文件
        .pipe(gulp.dest('./'));
}
