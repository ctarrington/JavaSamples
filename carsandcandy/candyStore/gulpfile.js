var gulp = require('gulp');
var concat = require('gulp-concat');

gulp.task('javascript', function() {
    return gulp.src([
                        'src/main/resources/static/bower_components/jquery/dist/jquery.min.js',
                        'src/main/resources/static/bower_components/handlebars/handlebars.js',
                        'src/main/resources/static/bower_components/ember/ember-template-compiler.js',
                        'src/main/resources/static/bower_components/ember/ember.debug.js',
                        'src/main/resources/static/bower_components/ember-data/ember-data.js'
                    ])
        .pipe(concat('candy-store-lib.js'))
        .pipe(gulp.dest('src/main/resources/static/js'));
});

gulp.task('default', ['javascript']);