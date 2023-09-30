import fs from 'fs'

/**
 * Get environment variable value
 * 
 * @param {string} key 
 * @param {string} defaultValue 
 * @returns {string}
 */
export function env(key, defaultValue = "") {
  if (typeof process.env[`${key}_FILE`] !== 'undefined') {
    return fs.readFileSync(process.env[`${key}_FILE`]).toString().trim()
  }

  if (typeof process.env[key] !== 'undefined') {
    return process.env[key]
  }

  return defaultValue
}
