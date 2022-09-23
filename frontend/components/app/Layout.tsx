import Header from './header/Header';
import React from 'react';


export default function Layout(props: { children: React.ReactNode }) {
  return (
    <div>
      <Header />
      <main>
        <div className="mx-auto max-w-7xl py-6 sm:px-6 lg:px-8">
          {props.children}
        </div>
      </main>
    </div>
  )
}
