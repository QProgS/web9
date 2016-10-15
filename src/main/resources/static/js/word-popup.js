function ModalWord() {
    self = this;
    riot.observable(self)
}
var modalWord = new ModalWord();

$(document).on("mouseenter", '.active-content span', function () {
    var id = $(this).data("id");
    //TODO ajax
    var data = {
        name: id,
        descriptions: [
            {text: id + ' a continuous area or expanse that is free, available, or unoccupied.'},
            {text: 'the dimensions of height, depth, and width within which all things exist and move.'},
            {text: 'It was advertising some sort of company that sells or leases office space , I think.'}
        ]
    };
    modalWord.trigger('setWord', data);
});

riot.compile(function () {
    riot.mount('*');
});



