function showPosts(userId) {
    $.ajax({
        url: '/user/' + userId + '/posts',
        type: 'GET',
        success: function(posts) {
            $('#postsList').empty();
            posts.forEach(function(post) {
                $('#postsList').append('<li class="list-group-item">' + post.qSubject + '</li>');
            });
            $('#postsModal').modal('show');
        },
        error: function() {
            alert('게시글을 불러오는 데 실패했습니다.');
        }
    });
}