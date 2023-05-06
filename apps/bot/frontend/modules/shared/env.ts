import fs from 'fs'

export function env(key: string, defaultValue: string = ""): string {
  if (typeof process.env[`${key}_FILE`] !== 'undefined') {
    return fs.readFileSync(process.env[`${key}_FILE`] as string).toString().trim()
  }

  if (typeof process.env[key] !== 'undefined') {
    return process.env[key] as string
  }

  return defaultValue
}
