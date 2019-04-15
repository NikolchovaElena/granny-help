$(document).ready(function () {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $('#follow-button').click(function (event) {
        event.preventDefault();

        var id = $(this).attr('data-cause_id');

        var text = $(this).text();
        console.log($(this));

        if (text.includes('Follow cause')) {
            $.ajax({
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                url: '/user/follow/causes/' + id,
                type: 'POST',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                }
            })
                .done(function () {
                    $('#follow-clause').text('Unfollow cause');
                })
                .fail(function () {
                    console.log("Error while trying to follow page!");
                });
        }
        if (text.includes('Unfollow cause')) {
            $.ajax({
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                url: '/user/unfollow/causes/' + id,
                type: 'POST',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                }
            })
                .done(function () {
                    $('#follow-clause').text('Follow cause');
                })
                .fail(function () {
                    console.log("Error while trying to unfollow page!");
                });
        }
    });
});