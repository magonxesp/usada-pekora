export function isRegex(value: unknown): boolean {
  if (typeof value != 'string' || value == '') {
    return false
  }

  try {
    new RegExp(value)
  } catch (exception) {
    return false
  }

  return true
}

export function isNotEmpty(value: unknown): boolean {
  if (typeof value === 'undefined' || value === null) {
    return false
  }

  if (typeof value === 'string' && value === '') {
    return false
  }

  if (Array.isArray(value) && value.length == 0) {
    return false
  }

  return true
}
