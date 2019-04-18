$(document).ready(function () {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $('#btn-update-cart').click(function (event) {
        event.preventDefault();
        var table = $("#table").children("tbody").children("tr");

        var data = {
            products: []
        };

        for (var i = 0; i < table.length; i++) {
            var almost = ($(table[i]).children()[3]);
            var there = $(almost).find('input');

            var id = $(there[0]).val();
            var quantity = $(there[1]).val();

            console.log(id);
            console.log(quantity);

            data.products.push(
                {
                    id: id,
                    quantity: quantity
                }
            );
        }

        console.log(data);

        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            url: '/cart/update',
            type: 'POST',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            data: JSON.stringify(data)
        })
            .done(function () {
                window.location = '/cart';
            })
            .fail(function () {
                console.log("Could not update cart");
            });
    });
});