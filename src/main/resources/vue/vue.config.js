module.exports = {
    publicPath: '/vue',
    devServer: {
        host: 'localhost',
        port: 8080,
        proxy: 'http://localhost:8080'
    }
}