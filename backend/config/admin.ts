export default ({ env }) => ({
  auth: {
    secret: env('ADMIN_JWT_SECRET', '9a32861bbc26475f69a426fbf85eb1bc'),
  },
});
