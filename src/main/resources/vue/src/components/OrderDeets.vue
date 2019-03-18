<template>
    <p>Number of Line Items processed: {{ numLineItems }}</p>
</template>

<script>
export default {
    name: 'OrderDeets',
    data() {
        return {
            numLineItems: 0
        }
    },
    methods: {
        emitEvent () {
            this.$socket.emit('line_items', this.numLineItems)
        }
    },
    created () {
        this.$options.sockets.line_items = (data) => {
            console.log(data) // eslint-disable-line no-console
            this.numLineItems++;
        }
    }
}
</script>