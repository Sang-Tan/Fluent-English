DO
$$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM words) THEN
            INSERT INTO words (id, text, sound, image, vietnamese_meaning, difficulty)
            VALUES
                (
                    1,
                    'elephant',
                    '{
                      "url": "https://tts.langeek.co/read?text=ZWxlcGhhbnQ&hash=6111e07f888974ccff5ae05fafa4e727e31b5268&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/28766/original/elephant",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'con voi',
                    1
                ),
                (
                    2,
                    'hippopotamus',
                    '{
                      "url": "https://tts.langeek.co/read?text=aGlwcG9wb3RhbXVz&hash=4b19ac6524c5f96bc5e7d2c76007445870207248&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/24180/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'con hà mã',
                    1
                ),
                (
                    3,
                    'rhinoceros',
                    '{
                      "url": "https://tts.langeek.co/read?text=cmhpbm9jZXJvcw&hash=11d55589a3882e437d9d637625e9395aea371933&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/24314/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'con tê giác',
                    2
                ),
                (
                    4,
                    'giraffe',
                    '{
                      "url": "https://tts.langeek.co/read?text=Z2lyYWZmZQ&hash=a9b94e44e1cc4accd0fbe2aed659fb014ada71db&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/17981/original/giraffe",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'con hươu cao cổ',
                    4
                ),
                (
                    5,
                    'gorilla',
                    '{
                      "url": "https://tts.langeek.co/read?text=Z29yaWxsYQ&hash=010537f173750599bb324ab399953e652cc85767&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/17985/original/gorilla",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'con khỉ đột',
                    2
                ),
                (
                    6,
                    'bear',
                    '{
                      "url": "https://tts.langeek.co/read?text=YmVhcg&hash=93319776de5f090eeb7a179bc36710dd7729ec1f&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/27195/original/bear",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'con gấu',
                    1
                ),
                (
                    7,
                    'panda',
                    '{
                      "url": "https://tts.langeek.co/read?text=cGFuZGE&hash=3bc8e8bf2c1e07412562f86458c6c4be1389dcf9&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/24164/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'con gấu trúc',
                    1
                ),
                (
                    8,
                    'moose',
                    '{
                      "url": "https://tts.langeek.co/read?text=bW9vc2U&hash=a24675de3e9140fbcece14e9b9061b314608913e&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/33904/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'con nai sừng tấm',
                    2
                ),
                (
                    9,
                    'eland',
                    '{
                      "url": "https://tts.langeek.co/read?text=Z2lhbnQgZWxhbmQ&hash=3841f397e4ce305e1ef84faac2cf9487f92f83df&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/33900/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'linh dương',
                    3
                ),
                (
                    10,
                    'hog',
                    '{
                      "url": "https://tts.langeek.co/read?text=Z2lhbnQgZm9yZXN0IGhvZw&hash=e58fd36daea8222116e98da7b304fa1d768f3a7a&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/24376/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'con lợn',
                    4
                ),
                (
                    11,
                    'wolf',
                    '{
                      "url": "https://tts.langeek.co/read?text=d29sZg&hash=6be53fb6519a48c41e02f4e04884f1fa8ed86859&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/17917/original/wolf",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'con sói',
                    1
                ),
                (
                    12,
                    'fox',
                    '{
                      "url": "https://tts.langeek.co/read?text=Zm94&hash=6dbc18df7f7b616f8e32fc6924313ed401ec5551&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/29261/original/fox",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'con cáo',
                    1
                ),
                (
                    13,
                    'lion',
                    '{
                      "url": "https://tts.langeek.co/read?text=bGlvbg&hash=ab74fe6d5caa031d802e102fbd155c1646564353&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/28333/original/lion",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'con sư tử',
                    1
                ),
                (
                    14,
                    'tiger',
                    '{
                      "url": "https://tts.langeek.co/read?text=dGlnZXI&hash=87294c9efa079ffe3afd9764f8acdb5aaf9c2d88&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/29222/original/tiger",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'con hổ',
                    2
                ),
                (
                    15,
                    'jaguar',
                    '{
                      "url": "https://tts.langeek.co/read?text=amFndWFy&hash=90edc62fe627fcff28a79fe85c67a14817f3d93c&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/24192/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'con báo đốm',
                    3
                ),
                (
                    16,
                    'wildcat',
                    '{
                      "url": "https://tts.langeek.co/read?text=d2lsZGNhdA&hash=3090d79225ae108babca976d7c3b93c5607333e5&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/24230/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'con mèo rừng',
                    4
                ),
                (
                    17,
                    'puma',
                    '{
                      "url": "https://tts.langeek.co/read?text=cHVtYQ&hash=f86bc0a0ef9e5f647d1c2eab28c63d20f19b869c&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/24761/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'báo sư tử',
                    5
                ),
                (
                    18,
                    'beefy',
                    '{
                      "url": "https://tts.langeek.co/read?text=YmVlZnk&hash=da4c56755aa81087e7f2a97227d3bc6858711a27&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/21899/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'lực lưỡng',
                    3
                ),
                (
                    19,
                    'brawny',
                    '{
                      "url": "https://tts.langeek.co/read?text=YnJhd255&hash=d8ced9efe829f5ade130413d915f9f40c2a9b36b&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/16284/original/muscular",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'rắn chắc',
                    1
                ),
                (
                    20,
                    'gangly',
                    '{
                      "url": "https://tts.langeek.co/read?text=Z2FuZ2x5&hash=d52fa23b224e9d7a66ca2fcb3c6b160537fe4c27&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/26495/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'cao gầy',
                    2
                ),
                (
                    21,
                    'fat',
                    '{
                      "url": "https://tts.langeek.co/read?text=ZmF0&hash=0e7432c9d518f59f91f5293a522793ca9ec23c2b&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/28926/original/fat",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'mập',
                    1
                ),
                (
                    22,
                    'obese',
                    '{
                      "url": "https://tts.langeek.co/read?text=b2Jlc2U&hash=f52c4710a1be26d0369a84b5a376e9b4dcb1d607&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/19252/original/obesity",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'béo phì',
                    4
                ),
                (
                    23,
                    'skinny',
                    '{
                      "url": "https://tts.langeek.co/read?text=c2tpbm55&hash=989fe261c0669ed6dd71d51a7a3c005ea33e1946&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/16282/original/slim",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'gầy',
                    5
                ),
                (
                    24,
                    'fastidious',
                    '{
                      "url": "https://tts.langeek.co/read?text=ZmFzdGlkaW91cw&hash=f144b7eb419faf55e0a46ef6db6d23b7baf93d2a&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "data:image/jpeg;base64,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",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'khó tính',
                    6
                ),
                (
                    25,
                    'scruffy',
                    '{
                      "url": "https://tts.langeek.co/read?text=c2NydWZmeQ&hash=fbb71a3af03ca1b4b934a95aa72e5c70612565cf&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/39721/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'nhếch nhác',
                    1
                ),
                (
                    26,
                    'brain',
                    '{
                      "url": "https://tts.langeek.co/read?text=YnJhaW4&hash=680e4f8d64ce52f344e7cd50b0559b751969d388&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/27008/original/brain",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'não bộ',
                    2
                ),
                (
                    27,
                    'nerve',
                    '{
                      "url": "https://tts.langeek.co/read?text=bmVydmU&hash=bf003f4121aec7288370090f8956756f73fc1247&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/32576/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'dây thần kinh',
                    3
                ),
                (
                    28,
                    'tonsil',
                    '{
                      "url": "https://tts.langeek.co/read?text=dG9uc2ls&hash=37da34481fe1cb039b8144cd4db5a93bec933358&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/30365/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'amiđan',
                    4
                ),
                (
                    29,
                    'spleen',
                    '{
                      "url": "https://tts.langeek.co/read?text=c3BsZWVu&hash=2029161a41dff9964ef4586a630f93e51ee0def4&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/32100/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'lách',
                    3
                ),
                (
                    30,
                    'face',
                    '{
                      "url": "https://tts.langeek.co/read?text=ZmFjZQ&hash=f7595e52373dd3b4089ceb5fb45e0ae7c01b59d3&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/23789/original/face",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'khuôn mặt',
                    1
                ),
                (
                    31,
                    'eyebrow',
                    '{
                      "url": "https://tts.langeek.co/read?text=ZXllYnJvdw&hash=12e453b0840e0af14f51f9a1506e1f516772e4a2&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/23803/original/eyebrow",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'lông mày',
                    2
                ),
                (
                    32,
                    'cheek',
                    '{
                      "url": "https://tts.langeek.co/read?text=Y2hlZWs&hash=6b03fd084933a4e0341f58aa2abebc371fb4f29a&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/17655/original/cheek",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'má',
                    2
                ),
                (
                    33,
                    'dimple',
                    '{
                      "url": "https://tts.langeek.co/read?text=ZGltcGxl&hash=810fb652e1249c848e5c6addcf461021d39beac7&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/30016/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'lúm đồng tiền',
                    3
                ),
                (
                    34,
                    'chin',
                    '{
                      "url": "https://tts.langeek.co/read?text=Y2hpbg&hash=174d607a349e5e6cfaf3af557823197aef33aec1&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/17657/original/chin",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'cái cằm',
                    4
                ),
                (
                    35,
                    'forehead',
                    '{
                      "url": "https://tts.langeek.co/read?text=Zm9yZWhlYWQ&hash=a29e5504ddb19bf9c8f764cc1fa367b7dc5de90c&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/17665/original/forehead",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'trán',
                    5
                ),
                (
                    36,
                    'lip',
                    '{
                      "url": "https://tts.langeek.co/read?text=bGlw&hash=fa1cbd7d95b7698a4cfbaa75f2f0e9eaf872e010&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/23809/original/lip",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'môi',
                    6
                ),
                (
                    37,
                    'nose',
                    '{
                      "url": "https://tts.langeek.co/read?text=bm9zZQ&hash=65e4d6c128026d14e1d23b5b45ffe40507c0f09e&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/17679/original/nose",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'mũi',
                    1
                ),
                (
                    38,
                    'ear',
                    '{
                      "url": "https://tts.langeek.co/read?text=ZWFy&hash=a7e8b7c6e05285e6e7e890856e9888b01b6b79cd&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/23793/original/ear",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'tai',
                    2
                ),
                (
                    39,
                    'eye',
                    '{
                      "url": "https://tts.langeek.co/read?text=ZXll&hash=f6db2d27d3e734403e211643841eb4f89c653156&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/17663/original/eye",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'mắt',
                    3
                ),
                (
                    40,
                    'mouth',
                    '{
                      "url": "https://tts.langeek.co/read?text=bW91dGg&hash=333caa91abe8c94fb4aeef40d7f27cd058122979&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/17675/original/mouth",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'miệng',
                    4
                ),
                (
                    41,
                    'kilt',
                    '{
                      "url": "https://tts.langeek.co/read?text=a2lsdA&hash=dee00bb57445af4bd1c4a18d95c3dceafc44165f&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/24949/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'váy dài',
                    5
                ),
                (
                    42,
                    'kimono',
                    '{
                      "url": "https://tts.langeek.co/read?text=a2ltb25v&hash=10f0b6453bc06fb1a055e8b95194de4e55c1f89d&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/24951/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'kimono',
                    6
                ),
                (
                    43,
                    'sari',
                    '{
                      "url": "https://tts.langeek.co/read?text=c2FyaQ&hash=0937d48a9770cc068366f1b81655458aab04652f&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/26812/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'sari',
                    1
                ),
                (
                    44,
                    'hijab',
                    '{
                      "url": "https://tts.langeek.co/read?text=aGlqYWI&hash=c7378604a6895556a323bf7d7ca41758b58079a6&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/24924/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'khăn trùm đầu',
                    2
                ),
                (
                    45,
                    'trend',
                    '{
                      "url": "https://tts.langeek.co/read?text=dHJlbmQ&hash=b718ecb23f62dae94d18225643e6b396d6eee738&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/33960/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'xu hướng',
                    3
                ),
                (
                    46,
                    'retro',
                    '{
                      "url": "https://tts.langeek.co/read?text=cmV0cm8&hash=863fa77863d8a33bc8900eace364258a097b5edc&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/34097/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'cổ điển',
                    4
                ),
                (
                    47,
                    'trendy',
                    '{
                      "url": "https://tts.langeek.co/read?text=dHJlbmR5&hash=41bdd2f0e3f0fc80abcb766c6ded5c26bcaf9cf6&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/18395/original/trendy",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'hợp thời trang',
                    5
                ),
                (
                    48,
                    'normcore',
                    '{
                      "url": "https://tts.langeek.co/read?text=bm9ybWNvcmU&hash=ac7debf61a8756f2c92dbbd87b4ce07fc8beb5e9&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/16280/original/casual",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'tiêu chuẩn',
                    6
                ),
                (
                    49,
                    'stylishness',
                    '{
                      "url": "https://tts.langeek.co/read?text=c3R5bGlzaG5lc3M&hash=73f13b7adf56642c88b7254d544bedecb942fec5&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/18437/original/fashionable",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'sự sành điệu',
                    1
                ),
                (
                    50,
                    'comfortable',
                    '{
                      "url": "https://tts.langeek.co/read?text=Y29tZm9ydGFibGU&hash=21e3418dd754d6de3590bbc44dd414d9d694b33a&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/20607/original/sporty",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'thoải mái',
                    2
                ),
                (
                    51,
                    'dressy',
                    '{
                      "url": "https://tts.langeek.co/read?text=ZHJlc3N5&hash=f66e592551091fffc486b3569c1f02b4e80fe87b&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/27564/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'ăn mặc đẹp',
                    3
                ),
                (
                    52,
                    'elegant',
                    '{
                      "url": "https://tts.langeek.co/read?text=ZWxlZ2FudA&hash=0032f411164d198fe4846932f172dee3ad723fed&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/33995/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'thanh lịch',
                    4
                ),
                (
                    53,
                    'flamboyant',
                    '{
                      "url": "https://tts.langeek.co/read?text=ZmxhbWJveWFudA&hash=690ec7cdd0dd235caf88d6d10da6fd6dd6d64a91&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/34085/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'rực rỡ',
                    5
                ),
                (
                    54,
                    'frumpy',
                    '{
                      "url": "https://tts.langeek.co/read?text=ZnJ1bXB5&hash=a3c9fa0a53500e3ad219bbf7310a092c8b6c0ee0&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/34087/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'lôi thôi',
                    6
                ),
                (
                    55,
                    'sporty',
                    '{
                      "url": "https://tts.langeek.co/read?text=c3BvcnR5&hash=3ae4ce98b984d8ac1f9412189a483a15e74b2d34&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/20607/original/sporty",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'thể thao',
                    1
                ),
                (
                    56,
                    'gaudy',
                    '{
                      "url": "https://tts.langeek.co/read?text=Z2F1ZHk&hash=8c04758d3c5eb5a86e42f45d962b66c8c782a215&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/34089/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'xa hoa',
                    2
                ),
                (
                    57,
                    'streetwear',
                    '{
                      "url": "https://tts.langeek.co/read?text=c3RyZWV0d2Vhcg&hash=0080ab54fd6970add5f6cf24e1a970a8d7c122c4&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/18982/original/common",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'quần áo đường phố',
                    3
                ),
                (
                    58,
                    'baggy',
                    '{
                      "url": "https://tts.langeek.co/read?text=YmFnZ3k&hash=23996af322decce51165daad85d383478eb02cbf&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/18329/original/baggy",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'rộng rãi',
                    4
                ),
                (
                    59,
                    'instrument',
                    '{
                      "url": "https://tts.langeek.co/read?text=aW5zdHJ1bWVudA&hash=f4101bfbab90dc326fa9247554e8eac1f8d8e1f4&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/27441/original/instrument",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'dụng cụ',
                    5
                ),
                (
                    60,
                    'musician',
                    '{
                      "url": "https://tts.langeek.co/read?text=bXVzaWNpYW4&hash=c88f3fdfa3b057a881d43e1f0d5f64d748080b73&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/36888/original/musician",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'nhạc sĩ',
                    6
                ),
                (
                    61,
                    'voice',
                    '{
                      "url": "https://tts.langeek.co/read?text=dm9pY2U&hash=2d837e39112a19291355bd3fb17d48f4f76a110f&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/26198/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'tiếng nói',
                    1
                ),
                (
                    62,
                    'busking',
                    '{
                      "url": "https://tts.langeek.co/read?text=YnVza2luZw&hash=caeca131254b96975da6e818ec44f6fc67b510d0&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/30261/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'hát rong',
                    2
                ),
                (
                    63,
                    'ensemble',
                    '{
                      "url": "https://tts.langeek.co/read?text=ZW5zZW1ibGU&hash=4de05292d56d76ee4f377964355ec9dc9ba244f6&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/18567/original/chorus",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'hòa tấu',
                    3
                ),
                (
                    64,
                    'auditorium',
                    '{
                      "url": "https://tts.langeek.co/read?text=YXVkaXRvcml1bQ&hash=bde50442406714bf72a1da270c0451f0af1581cd&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/30614/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'khán phòng',
                    4
                ),
                (
                    65,
                    'baton',
                    '{
                      "url": "https://tts.langeek.co/read?text=YmF0b24&hash=9d57478bf8dc4a0e2971340b138945143b4d63bf&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/30561/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'dùi cui',
                    5
                ),
                (
                    66,
                    'audience',
                    '{
                      "url": "https://tts.langeek.co/read?text=YXVkaWVuY2U&hash=e49791df32fe6509887447d58e8cec6db54ead05&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/22363/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'khán giả',
                    6
                ),
                (
                    67,
                    'argument',
                    '{
                      "url": "https://tts.langeek.co/read?text=YXJndW1lbnQ&hash=72760f1d8bf8d1677b622f67a2325c945720f347&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/20936/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'lý lẽ',
                    1
                ),
                (
                    68,
                    'consensus',
                    '{
                      "url": "https://tts.langeek.co/read?text=Y29uc2Vuc3Vz&hash=092e631975012462e70753d6729c712ee7eff04a&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/19418/original/loyal",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'đoàn kết',
                    2
                ),
                (
                    69,
                    'critic',
                    '{
                      "url": "https://tts.langeek.co/read?text=Y3JpdGlj&hash=266c0e2447147fd083763b01e899eeead788d119&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/19084/original/critic",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'nhà phê bình',
                    3
                ),
                (
                    70,
                    'forceful',
                    '{
                      "url": "https://tts.langeek.co/read?text=Zm9yY2VmdWw&hash=bd10b5cee7d621644b4612220ef5adfd22116d91&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/45842/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'mạnh mẽ',
                    4
                ),
                (
                    71,
                    'bun',
                    '{
                      "url": "https://tts.langeek.co/read?text=YnVu&hash=adf5c72662827518d435b58dc82ea1b21e4d7420&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/19102/original/bread-roll",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'bánh mỳ tròn',
                    5
                ),
                (
                    72,
                    'cornbread',
                    '{
                      "url": "https://tts.langeek.co/read?text=Y29ybmJyZWFk&hash=c9adcd09b176730e9bb59ca01e8e3b2be17758ab&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/31157/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'bánh ngô',
                    6
                ),
                (
                    73,
                    'crispbread',
                    '{
                      "url": "https://tts.langeek.co/read?text=Y3Jpc3BicmVhZA&hash=1751e5eb9a6da3a61d85c5de5803856072f12e1b&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/22133/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'bánh mỳ giòn',
                    1
                ),
                (
                    74,
                    'flatbread',
                    '{
                      "url": "https://tts.langeek.co/read?text=ZmxhdGJyZWFk&hash=e4b80298cc3bc78299dc7101c3c25012c6c3e8e9&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/27750/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'bánh mỳ cắt lát',
                    2
                ),
                (
                    75,
                    'breadstick',
                    '{
                      "url": "https://tts.langeek.co/read?text=YnJlYWRzdGljaw&hash=5bf80809b11f589ef0dd62e2e8c9b9c271f4e2e0&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/26345/original/bread-stick",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'bánh mì que',
                    3
                ),
                (
                    76,
                    'milk',
                    '{
                      "url": "https://tts.langeek.co/read?text=bWlsaw&hash=f67c76a77a7fe5bd77105803f37d3b6dd006135c&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/26988/original/milk",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'sữa',
                    4
                ),
                (
                    77,
                    'juice',
                    '{
                      "url": "https://tts.langeek.co/read?text=anVpY2U&hash=8516857d091968689303dc9041da0169ab014da3&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/18353/original/juice",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'nước ép',
                    5
                ),
                (
                    78,
                    'smoothie',
                    '{
                      "url": "https://tts.langeek.co/read?text=c21vb3RoaWU&hash=1bebab9070624daaca63f415d6b46a66c2b4f5bb&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/18249/original/smoothie",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'sinh tố',
                    6
                ),
                (
                    79,
                    'nonalcoholic',
                    '{
                      "url": "https://tts.langeek.co/read?text=bm9uYWxjb2hvbGlj&hash=36c89bb32e3fe6d4fc89db8ef9b25be77902cc72&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/19048/original/soft-drink",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'không cồn',
                    1
                ),
                (
                    80,
                    'cocktail',
                    '{
                      "url": "https://tts.langeek.co/read?text=Y29ja3RhaWw&hash=484bee160b4e81fc501fb18005680707f8b55330&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/18349/original/cocktail",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'rượu cocktail',
                    2
                ),
                (
                    81,
                    'cider',
                    '{
                      "url": "https://tts.langeek.co/read?text=Y2lkZXI&hash=c600c489f0568a4f45217582f4403a1a46885652&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/18293/original/cider",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'rượu táo',
                    3
                ),
                (
                    82,
                    'beer',
                    '{
                      "url": "https://tts.langeek.co/read?text=YmVlcg&hash=859e18ca4f8f63c683041ecf38e9c62280d4005a&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/18339/original/beer",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'bia',
                    4
                ),
                (
                    83,
                    'wine',
                    '{
                      "url": "https://tts.langeek.co/read?text=d2luZQ&hash=4f9e0e7eb2876257cd7105d69fff57401e236009&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/18365/original/wine",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'rượu vang',
                    5
                ),
                (
                    84,
                    'carbonated',
                    '{
                      "url": "https://tts.langeek.co/read?text=Y2FyYm9uYXRlZA&hash=7c0e184dca2c215c5db247b33e0e3706b89b5009&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/19558/original/sparkling",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'có ga',
                    6
                ),
                (
                    85,
                    'cocoa',
                    '{
                      "url": "https://tts.langeek.co/read?text=Y29jb2E&hash=563f09ae0f502d918ffa37ce6afe62c149246102&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/34305/original/cocoa",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'cacao',
                    1
                ),
                (
                    86,
                    'living room',
                    '{
                      "url": "https://tts.langeek.co/read?text=bGl2aW5nIHJvb20&hash=037641ac7ccd35a97a83310e5a20f56249a9f3aa&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/22165/original/living-room",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'phòng khách',
                    2
                ),
                (
                    87,
                    'bedroom',
                    '{
                      "url": "https://tts.langeek.co/read?text=YmVkcm9vbQ&hash=0bbd03af86fc3c3258a01c30d0da8fe5851ab63d&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/22107/original/bedroom",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'phòng ngủ',
                    3
                ),
                (
                    88,
                    'kitchen',
                    '{
                      "url": "https://tts.langeek.co/read?text=a2l0Y2hlbg&hash=424c434e57ae7d314a1b9172171eaf9d5e85ee41&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/22167/original/kitchen",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'nhà bếp',
                    4
                ),
                (
                    89,
                    'bathroom',
                    '{
                      "url": "https://tts.langeek.co/read?text=YmF0aHJvb20&hash=1df0dd490c01630a275169bf21768dc890a57cab&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/22111/original/bathroom",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'phòng tắm',
                    5
                ),
                (
                    90,
                    'dining room',
                    '{
                      "url": "https://tts.langeek.co/read?text=ZGluaW5nIHJvb20&hash=32b08af606ba48c7624351561a19272f34f42fd7&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/22163/original/dining-room",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'phòng ăn',
                    6
                ),
                (
                    91,
                    'laundry room',
                    '{
                      "url": "https://tts.langeek.co/read?text=bGF1bmRyeSByb29t&hash=85562b21620e6c70a399341755cd7010de4c67a3&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/28714/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'phòng giặt',
                    1
                ),
                (
                    92,
                    'playroom',
                    '{
                      "url": "https://tts.langeek.co/read?text=cGxheXJvb20&hash=bf3ddc896f1fa67e9a2770424918ca8af70a4fcb&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/28712/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'phòng chơi',
                    2
                ),
                (
                    93,
                    'mudroom',
                    '{
                      "url": "https://tts.langeek.co/read?text=bXVkcm9vbQ&hash=72b417cf91fad64581d6635afa37ac547e130e94&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/28634/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'phòng chứa đồ',
                    3
                ),
                (
                    94,
                    'library',
                    '{
                      "url": "https://tts.langeek.co/read?text=bGlicmFyeQ&hash=d6807e6488f505253b10cfa3a567d8dcb70e8e76&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/28710/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'thư viện',
                    4
                ),
                (
                    95,
                    'sunroom',
                    '{
                      "url": "https://tts.langeek.co/read?text=c3Vucm9vbQ&hash=7c985918303fb838bb51f71e6d5ad4df322d3c7e&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/42892/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'phòng tắm nắng',
                    5
                ),
                (
                    96,
                    'attic',
                    '{
                      "url": "https://tts.langeek.co/read?text=YXR0aWM&hash=fae0f58e14ca388099dcbdebcecf519e96886594&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/20976/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'gác xép',
                    6
                ),
                (
                    97,
                    'basement',
                    '{
                      "url": "https://tts.langeek.co/read?text=YmFzZW1lbnQ&hash=29d6fb34da6437a115a2119da816a62995183eff&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/14142/original/basement",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'tầng hầm',
                    1
                ),
                (
                    98,
                    'sauna',
                    '{
                      "url": "https://tts.langeek.co/read?text=c2F1bmE&hash=be9ef5b0903f29385b195096f1733f3be5610338&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/28646/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'xông hơi',
                    2
                ),
                (
                    99,
                    'cushion',
                    '{
                      "url": "https://tts.langeek.co/read?text=Y3VzaGlvbg&hash=640b12031d1d1aaa946a61eaaebf0a32c114bb5c&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/16517/original/cushion",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'cái đệm',
                    3
                ),
                (
                    100,
                    'ornament',
                    '{
                      "url": "https://tts.langeek.co/read?text=b3JuYW1lbnQ&hash=c7cd2abf6edc69a1078e0b19a987047e2719c6cf&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/20189/original/decoration",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'vật trang trí',
                    4
                ),
                (
                    101,
                    'paint',
                    '{
                      "url": "https://tts.langeek.co/read?text=cGFpbnQ&hash=220fd4b0b773f7f0c5cbe84a31811994dc65c575&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/22237/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'sơn',
                    5
                ),
                (
                    102,
                    'wallpaper',
                    '{
                      "url": "https://tts.langeek.co/read?text=d2FsbHBhcGVy&hash=a6676cb4e105a337761516485f27adbf5f212002&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/35740/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'hình nền',
                    6
                ),
                (
                    103,
                    'vase',
                    '{
                      "url": "https://tts.langeek.co/read?text=dmFzZQ&hash=282d8e6cad0f9970ae1e78276a3abbbc4ea66167&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/19040/original/vase",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'lọ cắm hoa',
                    1
                ),
                (
                    104,
                    'sculpture',
                    '{
                      "url": "https://tts.langeek.co/read?text=c2N1bHB0dXJl&hash=07e33468d35269ea93aaebf01ccba4abbd08b4ba&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/47572/original/sculpture",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'bức tượng',
                    2
                ),
                (
                    105,
                    'tapestry',
                    '{
                      "url": "https://tts.langeek.co/read?text=dGFwZXN0cnk&hash=845ca3bbf93ee51e6c56977a3ffc5e8a04d4b85f&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/32500/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'thảm thêu',
                    3
                ),
                (
                    106,
                    'planter',
                    '{
                      "url": "https://tts.langeek.co/read?text=cGxhbnRlcg&hash=74d23f8030a5a56957c699a991c48b79210f2820&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/41854/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'chậu cây',
                    4
                ),
                (
                    107,
                    'lampshade',
                    '{
                      "url": "https://tts.langeek.co/read?text=bGFtcHNoYWRl&hash=fecaa12646c34f152cef3a730dde77ab03f8bc71&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/28401/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'chụp đèn',
                    5
                ),
                (
                    108,
                    'canopy',
                    '{
                      "url": "https://tts.langeek.co/read?text=Y2Fub3B5&hash=d343d01770a3083041c12dd40958f153a4a62949&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/41840/original/",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'mái hiên',
                    6
                ),
                (
                    109,
                    'mirror',
                    '{
                      "url": "https://tts.langeek.co/read?text=bWlycm9y&hash=9c81b0d2375685386d02c08fdf1e2d35f5372c84&lang=en&cache-only=0",
                      "type": "ext",
                      "mediaType": "audio"
                    }',
                    '{
                      "url": "https://cdn.langeek.co/photo/28335/original/mirror",
                      "type": "ext",
                      "mediaType": "image"
                    }',
                    'gương',
                    1
                );
        END IF;
    END;
$$;