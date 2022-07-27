export default ({ env }) => ({
  connection: {
    client: 'mysql',
    connection: {
      host: env('DATABASE_HOST', 'backend_database'),
      port: env.int('DATABASE_PORT', 3306),
      database: env('DATABASE_NAME', 'pekora_backend'),
      user: env('DATABASE_USERNAME', 'pekora_backend_usr'),
      password: env('DATABASE_PASSWORD', 'pekora_backend_pw'),
      ssl: env.bool('DATABASE_SSL', false),
    },
  },
});
