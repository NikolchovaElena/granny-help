$(document).ready(function () {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $('#btn-add-to-cart').click(function (event) {
        event.preventDefault();

        var id = $('#product-id').val();
        var quantity = $('#product-quantity').val();

        var data = {
            quantity: quantity,
            productId: id
        };

        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            url: '/cart/add',
            type: 'POST',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            data: JSON.stringify(data)
        })
            .done(function (cartSize) {
                $('#nav-cart').empty();
                $('#nav-cart').append(`<span class="count">${cartSize}</span>`);

                var p = $('<div>');
                p.attr('style', 'color: #730099');
                p.text(`${quantity} items added to your cart`);
                p.attr('id', 'add-to-cart-success');
                $('#cart-add-box').append(p);

                setTimeout(function () {
                    $('#add-to-cart-success').hide()
                }, 2000);
            })
            .fail(function () {
                console.log("Could not add to cart");
            });
    });
});