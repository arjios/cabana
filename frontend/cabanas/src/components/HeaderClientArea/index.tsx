import './styles.css'

import ProductLogo from '../ProductLogo';

export default function HeaderClientArea() {

    return (
        <header className="header_container">
            <div className="header_content">
                <ProductLogo />
                <p className="header_name">Cabanas</p>
                <p className="header_access">Entrar</p>
            </div>
        </header>
    );
}