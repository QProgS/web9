<word-popup>
    <div id="word-popup__outer">
        <div id="word-popup__overlay"></div>
        <div id="word-popup__block" class="popover fade bottom in">
            <div class="arrow"></div>
            <h3 class="popover-title"><span>{word.name}</span></h3>
            <div class="popover-content">
                <ol if="{word.definitions}" >
                    <li each="{word.definitions}">{text}</li>
                </ol>
            </div>
        </div>
    </div>

    <script>
        var self = this

        modalWord.on('setWord', function (word) {
            //console.log('on setWord', word);
            self.word = word;
            self.update()
        })
    </script>

</word-popup>
