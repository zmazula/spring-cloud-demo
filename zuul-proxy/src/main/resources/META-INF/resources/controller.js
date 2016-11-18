var app = angular.module("app", ["xeditable", "ngResource","ui.bootstrap"]);

app.run(function(editableOptions) {
  editableOptions.theme = 'bs3';
});

app.factory("MidBean", function ($resource) {
    return $resource("/mid_service/midBeans/:id", {id: "@midId"}, {
        update: {
            method: 'PUT'
        }
    });
});

app.controller('Ctrl', function($scope, $filter, filterFilter, $http, MidBean, filterFilter) {

  $scope.sortType     = 'customerName';
  $scope.sortReverse  = false;
  $scope.searchField   = '';

  $scope.filteredMids = [];
  $scope.currentPage = 1;
  $scope.numPerPage = 5;
  $scope.maxSize = 5;

  $scope.mids = MidBean.query(function(u, getResponseHeaders){
    $scope.filteredMids = filterFilter($scope.mids, {name: $scope.mids.midId});
	$scope.totalItems = $scope.filteredMids.length;
  });

  $scope.checkName = function(data, id) {
    if ( data == '') {
      return "Required field!";
    }
  };

  $scope.saveMid = function(data, id, index) {
    var midBean = new MidBean(data);
    if (id == '-') {
        midBean.$save(function(data){
           $scope.mids[0].midId = data.midId;
        });
    } else {
        angular.extend(data, {id: id});
        midBean.$update(data);
    }
    $scope.inserted = {};
  };

  $scope.removeMid = function(index, id) {
    MidBean.delete({id: id});
    $scope.mids.splice($scope.getIndexOf($scope.mids, id, "midId"), 1);
  };

  $scope.addMid = function() {
    $scope.inserted = {
      midId: '-',
      cartType: ''
    };
    $scope.mids.splice(0,0,$scope.inserted);
    $scope.setPage(1);
  };

  $scope.setPage = function (pageNo) {
      $scope.currentPage = pageNo;
    };

   $scope.getIndexOf = function(arr, val, prop) {
          var l = arr.length,
            k = 0;
          for (k = 0; k < l; k = k + 1) {
            if (arr[k][prop] === val) {
              return k;
            }
          }
          return false;
   }

    $scope.showCardCompany = function(midItem) {
       if(midItem.cardCompany && $scope.cardCompanies.length) {
         var selected = $filter('filter')($scope.cardCompanies, {text: midItem.cardCompany});
         return selected.length ? selected[0].text : 'Not set';
       } else {
         return midItem.cardCompany|| 'Not set';
       }
    };


   $scope.showCardNetworkType = function(midItem) {
       if(midItem.cardNetwork && $scope.cardNetworkTypes.length) {
         var selected = $filter('filter')($scope.cardNetworkTypes, {text: midItem.cardNetwork});
         return selected.length ? selected[0].text : 'Not set';
       } else {
         return midItem.cardNetwork|| 'Not set';
       }
    };

   $scope.cardNetworkTypes = [
       {value: 1, text: 'Visa'},
       {value: 2, text: 'MasterCard'},
       {value: 3, text: 'American Express'},
       {value: 4, text: 'Discover'}
   ];

   $scope.cardCompanies = [
          {value: 1, text: 'Chase'},
          {value: 2, text: 'Bank of America'},
          {value: 3, text: 'Wells Fargo'},
          {value: 4, text: 'U.S. Bank'},
          {value: 5, text: 'Citibank'},
          {value: 6, text: 'Capital One'},
          {value: 7, text: 'Rest'}
    ];


});
