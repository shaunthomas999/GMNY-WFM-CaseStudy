Template.layout.helpers({
  isCordova: function () {
    return Meteor.isCordova
  },
  isWeb: function () {
    return Meteor.isClient
  },
});