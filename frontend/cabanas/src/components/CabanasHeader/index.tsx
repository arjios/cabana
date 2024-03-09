import { NavLink } from 'react-router-dom';
import './styles.css';
import { Link, Outlet } from 'react-router-dom';

export default function CabanasHeader() {

    return (
        <>
            <header className="header_container">
                <section className="header_content">
                    <div className="header_logo" >
                        <Link to="/">
                            <input className="logo_button" type="submit" value="Cabanas" id="cabanas" />
                        </Link>
                    </div>
                    <div className="header_options" >
                        <a href="about" className="options_button" id="option_about">
                            <NavLink to="/about" >
                                <input className="options_button" type="submit" value="Sobre" id="about" />
                            </NavLink>
                        </a>
                        <a href="menu" className="options_button" id="option_menu">
                            <Link to="/menu" >
                                <input className="options_button" type="submit" value="Cardapio" id="menu" />
                            </Link>
                        </a>
                        <a href="contact" className="options_button" id="option_contact">
                            <Link to="/contact">
                                <input className="options_button" type="submit" value="Contato" id="contact" />
                            </Link>
                        </a>
                    </div>
                </section>
            </header>
            <Outlet />
        </>
    );

}