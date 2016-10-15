riot.mount('*');

function ModalWord() {
    self = this;
    var word = null;

    self.setWord = function(word) {
        if(self.word != null) self.word.removeClass('active');
        self.word = word;
        self.word.addClass('active');
        self.word.parent().addClass("word-selected");
    };
    self.unsetWord = function() {
        if(self.word != null) {
            self.word.removeClass('active');
            self.word.parent().removeClass("word-selected");
        }
        self.word = null;
    };

    riot.observable(self)
}
var modalWord = new ModalWord();

$(document).on("click", ".active-content span", function () {
    var self = $(this);
    var id = self.data("id");
    if(id == undefined){console.error(".active-content span data-id - is undefined"); return;}

    $.getJSON( "/words/json/" + id, function( data ) {
        if(data == null)console.error("/words/json/" + id + " is null");
        else {
            modalWord.trigger('setWord', data);
            modalWord.setWord(self);
            showWordPopup(self);
        }
    });

});

function showWordPopup(word) {
    $('#word-popup__overlay').css("display", "block");

    $('#word-popup__block')
        .css("top", word.offset().top - word.parent().offset().top + 19)
        .css("position", "absolute")
        .css("width",     word.parent().width())
        .css("max-width", word.parent().width())
        .css("display", "block");

    $('#word-popup__block .arrow')
        .css("left", word.offset().left + word.width()/2.0 - word.parent().offset().left);

    var title = $('#word-popup__block .popover-title span');
    title.css("left", word.offset().left + word.width()/2.0  - word.parent().offset().left
        - title.width()/2.0);

}

$(document).on("click", "#word-popup__overlay", function () {
    $('#word-popup__overlay').css("display", "none");
    $('#word-popup__block').css("display", "none");
    modalWord.unsetWord();
});




