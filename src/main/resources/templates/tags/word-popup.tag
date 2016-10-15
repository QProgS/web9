<word-popup>
    <h3>{word.name}</h3>
    <ol if="{word.descriptions}" >
        <li each="{word.descriptions}">{text}</li>
    </ol>

    <script>
        var self = this

        modalWord.on('setWord', function (word) {
            //console.log(word);
            self.word = word;
            self.update()
        })
    </script>

    <style>
        ol{
            font-size: 14px;
        }
    </style>
</word-popup>
