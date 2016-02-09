var gulp = require('gulp'),
    sass = require('gulp-sass'),
    concat = require('gulp-concat'),
    cssnano = require('gulp-cssnano');

gulp.task('styles', function() {
    gulp.src('app/**/*.scss')
        .pipe(sass().on('error', sass.logError))
	.pipe(concat('app.css'))
	.pipe(cssnano())
        .pipe(gulp.dest('app/'));
});

gulp.task('watch', function () {
    gulp.watch('app/**/*.scss', function (event) {
	console.log('File ' + event.path + ' was ' + event.type + ', running tasks...');
	gulp.run('styles');
    });
});
