import ButtonOrder from '../../components/ButtonOrder';
import ButtonOut from '../../components/ButtonOut';
import HeaderClientArea from '../../components/HeaderClientArea';
import HeaderInfoArea from '../../components/HeaderInfoArea';
import ProductDetailsCard from '../../components/ProductDetailsCard';
import './styles.css'

export default function ProductDetails() {

  return (
    <>
      <HeaderClientArea />
      <HeaderInfoArea />
      <ProductDetailsCard />
      <ButtonOut />
      <ButtonOrder />
    </>
  );
}