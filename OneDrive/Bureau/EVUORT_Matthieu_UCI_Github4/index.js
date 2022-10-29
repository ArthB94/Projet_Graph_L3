const express = require('express');
const app = express();
const port = 3000;

let length;

app.use(express.json());
app.use(express.urlencoded({ extended: false }))

var knex = require("knex")({
    client: 'pg',
    connection: {
        host: 'localhost',
        user: 'postgres',
        database: 'anime_db',
        password: 'Ma21)Ev19M'
    },

    migrations:{
        tableName: "knex_migrations"
    },
    pool: {
        min: 2,
        max: 10
    }
});

app.get('/', function (req, res) {
    res.sendFile(__dirname + "/index.html")
});

app.get('/animes', (req, res) => {
    knex('animes')
        .select({ id: 'id', name: 'name', studio: 'studio' }).orderBy('id')
        .then((animes) => res.status(200).json(animes))
        .catch((err) => {
            console.error(err);
            return res.status(400).json({ success: false, message: 'An error occurred, please try again later.' });
        })
})

app.put("/update/:idAnime", async (req, res) => {
    await knex('animes')
        .where({ id: req.params.idAnime })
        .update({ 
            name: req.body.name, 
            studio: req.body.studio
        })
    return res.status(200).json({ success: true })
})

app.post("/create", async (req, res) => {
    const length = parseInt((await knex('animes').count("id"))[0].count) + 1
    await knex('animes').insert({ id: length, name: req.body.name, studio: req.body.studio })
    res.status(201).json({ name: req.body.name, studio: req.body.studio })
})

app.delete("/delete/:idAnime", async (req, res) => {
    await knex('animes').where({ id: req.params.idAnime }).del()
    return res.status(200).json({ success: true })
})

app.listen(port, () => {
    console.log(`Example app listening at http://localhost:${port}`);
});