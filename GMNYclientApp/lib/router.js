Router.configure({
  layoutTemplate: 'appMain'
});

Router.route('/', function () {
  this.render('home');
});

Router.route('/home', function () {
  this.render('home');
});

Router.route('/status', function () {
  this.render('status');
});

Router.route('/contact', function () {
  this.render('contact');
});