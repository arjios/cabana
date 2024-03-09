import './styles.css';

import BolinhoBacalhau from '../../assets/images/BolinhoBacalhau.png';

export default function ProductDetailsCard() {
    return (
        <main className="main_container">
            <section className="main_content">
                <div className="dsc_product_details">
                    <img className="dsc_product_details_img" src={BolinhoBacalhau} alt="BolinhoBacalhau" />
                    <div className="dsc_product_details_description">
                        <div className="dsc_product_details_content">
                            <p className="dsc_product_details_code">COD 105</p>
                            <p className="dsc_product_details_short"> - Bolinho de Bacalhau</p>
                        </div>
                        <div>
                            <p className="dsc_product_details_name">Descrição:</p>
                            <p className="dsc_product_details_name">Bolinho de Bacalhau com 4 unidades.</p>
                        </div>

                        <div className="dsc_product_details_price">
                            <p>R$ 45.90</p>
                        </div>
                    </div>
                </div>
            </section>
        </main>
    );
} 