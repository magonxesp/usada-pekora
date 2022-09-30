import { Disclosure } from '@headlessui/react'
import Image from 'next/future/image'
import style from './Header.module.css'
import logo from './logo.png'
import { useSession } from 'next-auth/react'
import Picture from '../../shared/image/Picture'


export default function Header() {
  const { data: session } = useSession()

  return (
    <header className={`border-b-4 ${style.header}`}>
      <div className="min-h-full">
        <Disclosure as="div" className="bg-white">
          <div className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
            <div className="flex h-20 items-center justify-between">
              <div className="flex-shrink-0 overflow-hidden h-20">
                <Image
                  src={logo}
                  alt="The header logo"
                  loading="eager"
                  className={`w-9/12 relative ${style.logo_img}`}
                />
              </div>
              {(session) ? (
                <div className="flex-shrink-0 flex items-center">
                  <Picture
                    src={session?.user?.image ?? ''}
                    className="rounded-full w-10 mr-3"
                    alt="User avatar"
                  />
                  <p>{session?.user?.name}</p>
                </div>
              ) : ''}
            </div>
          </div>
        </Disclosure>
      </div>
    </header>
  )
}
