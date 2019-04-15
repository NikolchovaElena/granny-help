$(document).ready(function () {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $('#send-comment').click(function (event) {
        event.preventDefault();

        var id = $('#cause-id').val();

        var data = {
            comment: $('#comment').val()
        };

        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            url: '/causes/' + id + '/comments',
            type: 'POST',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            data: JSON.stringify(data)
        })
            .done(function (data) {
                $('#message-board').prepend(`<div class="alert alert-primary">
                                       <small>${data.publishingDate} ${data.authorName} said :</small>
                                       <div>
                                             <strong>${data.comment}</strong>
                                       </div>
                            </div>`);
                $('#comment').val('');
            })
            .fail(function () {
                console.log("Could not load comment");
            });
    });
});