import React from 'react';

const TermsPage = () => {
  return (
    <div className="min-h-screen bg-[#F9FAFB] py-12 px-4">
      <div className="max-w-3xl mx-auto bg-white p-8 sm:p-12 rounded-3xl shadow-sm border border-gray-100">
        
        <h1 className="text-3xl font-bold text-gray-900 mb-2">Términos y Condiciones</h1>
        <p className="text-gray-500 mb-8 pb-8 border-b border-gray-100">Última actualización: 08 de Diciembre, 2025</p>

        <div className="space-y-8 text-gray-600 leading-relaxed">
            
            <section>
                <h2 className="text-xl font-bold text-gray-800 mb-3">1. Introducción</h2>
                <p>
                    Bienvenido a Sanus Corpus. Al acceder a nuestro sitio web y utilizar nuestros servicios, aceptas cumplir con los siguientes términos y condiciones. Te recomendamos leerlos cuidadosamente.
                </p>
            </section>

            <section>
                <h2 className="text-xl font-bold text-gray-800 mb-3">2. Uso del Sitio</h2>
                <p>
                    El contenido de este sitio es para información general y uso personal. Está sujeto a cambios sin previo aviso. Ni nosotros ni terceros ofrecemos garantía alguna sobre la exactitud, puntualidad o integridad de la información.
                </p>
            </section>

            <section>
                <h2 className="text-xl font-bold text-gray-800 mb-3">3. Compras y Pagos</h2>
                <p>
                    Todos los precios están en pesos chilenos (CLP) e incluyen IVA. Nos reservamos el derecho de modificar los precios en cualquier momento. El pago se procesa a través de plataformas seguras externas.
                </p>
            </section>

            <section>
                <h2 className="text-xl font-bold text-gray-800 mb-3">4. Política de Devoluciones</h2>
                <p>
                    Aceptamos devoluciones dentro de los 10 días posteriores a la compra, siempre que el producto esté sellado y en su embalaje original, presentando la boleta correspondiente.
                </p>
            </section>

             <section>
                <h2 className="text-xl font-bold text-gray-800 mb-3">5. Propiedad Intelectual</h2>
                <p>
                    Todo el contenido, logos, marcas y diseños en este sitio son propiedad de Sanus Corpus o han sido licenciados a nosotros. La reproducción está prohibida.
                </p>
            </section>

        </div>

        <div className="mt-12 pt-8 border-t border-gray-100 text-center">
            <button className="bg-gray-100 text-gray-600 px-6 py-2 rounded-full font-bold hover:bg-gray-200 transition-colors">
                Volver al Inicio
            </button>
        </div>

      </div>
    </div>
  );
};

export default TermsPage;