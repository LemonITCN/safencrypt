/**
 * Created by 1iURI on 2017/8/1.
 */
// 自动化构建工具
var gulp = require('gulp');
// gulp插件-js代码检测
var jshint = require('gulp-jshint');
// gulp插件-js合并
var concat = require('gulp-concat');
// gulp插件-js压缩
var uglify = require('gulp-uglify');
// gulp插件-文件重命名
var rename = require('gulp-rename');

// js源文件路径
var jsSrcPath = './core/**/*.js';
// js输出文件路径
var jsAimPath = './';
var outputFileName = 'safencrypt.js';

/**
 * step-1： 合并、压缩js
 */
gulp.task('buildSafencrypt', function () {
    gulp.src(jsSrcPath)                              // 需要操作的文件
        .pipe(concat(outputFileName))                // 合并所有js到common.js
        .pipe(gulp.dest(jsAimPath))                  // 输出到目标文件夹
        .pipe(rename({suffix: '.min'}))              // rename压缩后的文件名
        .pipe(uglify())                              // 压缩
        .pipe(gulp.dest(jsAimPath));                 // 输出
});

/**
 * step-2： 检查输出的js文件
 */
gulp.task('checkOutput', function () {
    gulp.src(jsAimPath + "")
        .pipe(jshint())
        .pipe(jshint.reporter('default'));
});

gulp.task('default', ['buildSafencrypt', 'checkOutput'], function () {
    // 监听源文件, 如果有变化就执行编译
    gulp.watch(jsSrcPath, ['buildSafencrypt', 'checkOutput']);
});