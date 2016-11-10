var myApp = angular.module('myApp', ['socket-io']);

myApp.controller('MessageController', function($scope, $http, socket)
{

  socket.on('refresh', function(){
    $scope.refresh();
    console.log('socket refreshing');
  });



  $scope.messagelist = [];
  $scope.users = [];
  $scope.body = true;
  $scope.login = false;

  $scope.enter = function()
  {
    $scope.usrInitials = [];

    $scope.users.push({user: $scope.initials});
    var url = "user";
    var jobj = JSON.stringify($scope.users);
    $http({
      method: "POST",
      url: url,
      data: jobj
    }).then(function successCallback(data)
      {

        var url = "user";
        $http({
          method: "GET",
          url: url
        }).then(function successCallback(response)
        {
          console.log("success");
          var items = response['data'];
          for (var i=0; i < items.length; i++)
          {
              $scope.usrInitials.push({user: items[i].user});
          }

        }, function errorCallback(response)
          {
            console.log("error");
            console.log(response);
          });
      }, function errorCallback(data)
      {
        console.log("error");
        console.log(data);
      });
      $scope.body = false;
      $scope.login = true;
      $scope.refresh();
  }

  $scope.onSend = function()
  {
    //on send i want to broadcast to client w/ matching agent code w/ socket.io

    $scope.date = new Date();
    // {{ date | date: short }}

    var count =0;
    $scope.messagelist.push({name: $scope.name, phoneNumber: $scope.phoneNumber, message: $scope.messageBody, agent: $scope.agent, sendr: $scope.initials, date: $scope.date, done: false});
    socket.emit('test', $scope.messagelist);

    $scope.name = "";
    $scope.phoneNumber = "";
    $scope.messageBody = "";
    var url = "message";
    var jobj = JSON.stringify($scope.messagelist);
    $http({
      method: "POST",
      url: url,
      data: jobj
    }).then(function successCallback(data)
    {
      $scope.messagelist = [];
      count++;
      if (count == 1)
      {
        $scope.refresh();
      }
    }, function errorCallback(data)
    {
      console.log("error");
      console.log(data);
    });

  }

  $scope.refresh = function()
  {
    $scope.specMessages = [];

    $scope.agentx = $scope.initials;
    var url = "message?q=" + $scope.agentx;
    $http({
      method: "GET",
      url: url
    }).then(function successCallback(response)
    {
      console.log(JSON.stringify(response));

      var items = response['data'];
      for (var i=0; i < items.length; i++)
      {
          $scope.specMessages.push({name: items[i].name, phoneNumber: items[i].phoneNumber, message: items[i].message, sendr: items[i].sendr, date: items[i].date});
      }

    }, function errorCallback(response)
    {
      console.log("error");
      console.log(response);
    });
  }

  $scope.removeMsg = function()
  {
    var count = 0;
    $scope.toDelete = [];
    for (var i=0; i < $scope.specMessages.length; i++)
    {
      if ($scope.specMessages[i].done)
      {
        $scope.toDelete.push({name: $scope.specMessages[i].name, phoneNumber: $scope.specMessages[i].phoneNumber, message: $scope.specMessages[i].message});
      }
    }
    for (var i=0; i < $scope.toDelete.length; i++)
    {
      var url = "delete";
      var jobj = JSON.stringify($scope.toDelete[i]);
      $http({
        method: "POST",
        url: url,
        data: jobj
      }).then(function successCallback(data)
      {
        console.log("success");
        if (count == $scope.toDelete.length - 1)
        {
          console.log("refreshing");
          $scope.refresh();
        }
        count++;

      }, function errorCallback(data)
      {

        console.log("error");
        console.log(data);
      });
    }

  }

});
