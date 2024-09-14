    $(document).ready(function() {
        console.log("ready");
        $.ajax({
          url: '/api/department/list',
          success: function (data) {
            console.log(data);
            let $div = $('div#main');
            data.forEach(el => {
                $div.append("<span style='display: block;'>" + el + "</span>")
            });
          },
          error: function(e) {
            console.log(e);
          },
          dataType: 'json'
        });
    });