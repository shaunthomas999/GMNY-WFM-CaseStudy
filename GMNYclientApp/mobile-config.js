App.info({
  name: 'GMNY',
  description: 'GMNY Customer Application',
  author: 'Shaun Thomas',
  email: 'shaunthomas999@gmail.com',
  version: '0.0.1'
});

App.icons({
  // iOS
  'iphone': 'resources/icons/icon-60x60.png',
  'iphone_2x': 'resources/icons/icon-60x60@2x.png',
  'ipad': 'resources/icons/icon-72x72.png',
  'ipad_2x': 'resources/icons/icon-72x72@2x.png',

  // Android
  'android_ldpi': 'resources/icons/favicon_36x36.png',
  'android_mdpi': 'resources/icons/favicon_48x48.png',
  'android_hdpi': 'resources/icons/favicon_72x72.png',
  'android_xhdpi': 'resources/icons/favicon_96x96.png'
});

App.setPreference('StatusBarOverlaysWebView', 'false');
App.setPreference('StatusBarBackgroundColor', '#000000');