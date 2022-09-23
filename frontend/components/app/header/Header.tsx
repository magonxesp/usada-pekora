import { Disclosure } from '@headlessui/react'
import Image from 'next/future/image'
import styles from './Header.module.css'
import logo from './logo.png'


export default function Header() {
  return (
    <header className={`border-b-4 ${styles.header}`}>
      <div className="min-h-full">
        <Disclosure as="div" className="bg-white">
          <div className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
            <div className="flex h-20 items-center justify-between">
              <div className={`flex-shrink-0 ${styles.logo}`}>
                <Image
                  src={logo}
                  alt="The header logo"
                />
              </div>
            </div>
          </div>
        </Disclosure>
      </div>
    </header>
  )
}
