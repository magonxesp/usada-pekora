<template>
  <header class="border-b-4">
    <div class="min-h-full">
      <Disclosure as="div" class="bg-white">
        <div class="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
          <div class="flex h-20 items-center justify-between">
            <div class="flex-shrink-0 logo">
              <img src="~/assets/images/logo.png" alt="The header logo"/>
            </div>
          </div>
        </div>
      </Disclosure>
    </div>
  </header>
</template>

<script setup>
import { Disclosure } from "@headlessui/vue"
</script>

<style scoped>
header {
  border-bottom-color: var(--header-border-color);
}

.logo {
  overflow: hidden;
  height: inherit;
}

.logo img {
  position: relative;
  top: -40%;
  width: 70%;
}

</style>
