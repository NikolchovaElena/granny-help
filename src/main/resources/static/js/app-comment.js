$(document).ready(function () {

    $('#send-comment').click(function (event) {
        event.preventDefault();

        var id = $('#cause-id').val();

        var data = {
            comment: $('#comment').val()
        };

        //  Na url si slagash tvoq a idto go podawash ili v id ili v custom attribute i go vzimash kakto po-gore sum pokazal
        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            url: '/causes/' + id + '/comments',
            type: 'POST',
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
// $.ajax({
//     url: '/p+rofiles/' + id + '/statistics',
//     type: 'GET'
// })
//     .done(function (data) {
//         fillCard(data.followers, 'line', '#followers', '#5969ff');
//         fillCard(data.following, 'line', '#following', '#e83e8c');
//         fillCard(data.posts, 'line', '#posts', '#5969ff');
//     })
//     .fail(function (error) { console.log(error); });

// On click -> your id of the button
// $('#{your_button_id}').click(function (event) {
//     event.preventDefault();
//     var id = $(this).attr('id');
//
//     // Na url si slagash tvoq a idto go podawash ili v id ili v custom attribute i go vzimash kakto po-gore sum pokazal
//     $.ajax({
//         url: '/profiles/' + id + '/statistics',
//         type: 'GET'
//     })
//         .done(function (data) {
//             // Change the button name and acction like if the button is a href you can change the href attribute with:
//             // $(this).attr('href', '{your_new_href_HERE}');
//         })
//         .fail(function (error) { console.log(error); });
// });