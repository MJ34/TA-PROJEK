$(document).ready(function() {
    $('.delete-book').on('click', function (e) {
        /*<![CDATA[*/
        var path = /*[[@{/}]]*/'remove';
        /*]]>*/
        var id= $(this).attr('id');

        bootbox.confirm({
            message: "apakah yakin akan hapus produk?",
            buttons: {
                cancel: {
                    label: '<i class="fa fa-times"></i> Cancel'
                },
                confirm: {
                    label: '<i class="fa fa-check"></i> Confirm'
                }
            },
            callback: function (confirmed) {
                if(confirmed) {
                    $.post(path, {'id': id}, function (res) {
                        location.reload();
                    });
                }
            }
        });
    });

    var produkIdList=[];

    $('.checkboxBook').click(function () {
        var id = $(this).attr('id');
        if(this.checked){
            produkIdList.push(id);
        }
        else {
            produkIdList.splice(produkIdList.indexOf(id), 1);
        }
    })

    $('#deleteSelected').click(function(){
        /*<![CDATA[*/
        var path = /*[[@{/}]]*/'removeList';
        /*]]>*/

        bootbox.confirm({
            message: "Apakah kamu yakin akan hapus produk?",
            buttons: {
                cancel: {
                    label: '<i class="fa fa-times"></i> Cancel'
                },
                confirm: {
                    label: '<i class="fa fa-check"></i> Confirm'
                }
            },
            callback: function (confirmed) {
                if(confirmed) {

                    $.ajax({
                        type: 'POST',
                        url: path,
                        data: JSON.stringify(produkIdList),
                        contentType: "application/json",
                        success: function(res) { console.log(res); location.reload(); },
                        error: function(res) {
                            console.log(res);
                            location.reload();
                        }
                    });
                }
            }
        });
    });

    $("#selectAllProduk").click(function(){
        if($(this).prop("checked") == true){
            $('.checkboxBook').click();
        }
        else if($(this).prop("checked") == false){
            $('.checkboxBook').click();
        }

    });
});