import fs from 'fs'

export function env(key, defaultValue = "") {
  if (typeof process.env[`${key}_FILE`] !== 'undefined') {
    return fs.readFileSync(process.env[`${key}_FILE`]).toString().trim()
  }

  if (typeof process.env[key] !== 'undefined') {
    return process.env[key]
  }

  return defaultValue
}
