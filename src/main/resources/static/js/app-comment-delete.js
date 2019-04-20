$(document).ready(function () {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $('#btn-delete-comment button').click(function (event) {
        event.preventDefault();
        var id = $(this).attr('comment_id');
        var causeId = $(this).attr('data-cause_id');

        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            url: '/delete/comment/' + id,
            type: 'POST',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            }
        })
            .done(function () {
                window.location = '/causes/' + causeId;
            })
            .fail(function () {
                console.log("Could not delete comment");
            });
    });
});