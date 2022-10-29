/**
 * @param { import("knex").Knex } knex
 * @returns { Promise<void> } 
 */
exports.seed = async function(knex) {
  // Deletes ALL existing entries
  await knex('animes').del()
  await knex('animes').insert([
    {id: 1, name: 'Steins Gate', studio: 'White Fox'},
    {id: 2, name: 'Kimetsu no Yaiba', studio: 'Ufotable'},
    {id: 3, name: 'Black Clover', studio: 'Pierrot'}
  ]);
};
