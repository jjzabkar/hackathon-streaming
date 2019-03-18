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
    }
}
</script>