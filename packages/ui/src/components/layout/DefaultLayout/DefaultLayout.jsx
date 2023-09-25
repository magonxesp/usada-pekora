import { Header } from '../../app/Header/Header'
import { MainContainer } from '../../app/MainContainer/MainContainer'
import { ToastContainer } from 'react-toastify'
import { ModalContainer } from '../../common/modal/ModalContainer/ModalContainer'

export function DefaultLayout({ children }) {
  return (
    <>
      <Header />
      <MainContainer>
        {children}
      </MainContainer>
      <ToastContainer />
      <ModalContainer />
    </>
  )
}
