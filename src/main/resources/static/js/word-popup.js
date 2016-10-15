function ModalWord() {
    self = this;
    riot.observable(self)
}
var modalWord = new ModalWord();

$(document).on("click", '.active-content span', function () {
    var id = $(this).data("id");

    $.getJSON( "/words/json/" + id, function( data ) {
        modalWord.trigger('setWord', data);
    });

});

riot.compile(function () {
    riot.mount('*');
});



