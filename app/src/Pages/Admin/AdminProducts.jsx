import React, { useEffect, useState } from 'react';
import { productService } from '../../services/productServices';
import { adminService } from '../../services/adminService';
import { categories } from '../../data/categories';



const AdminProducts = () => {
    const [products, setProducts] = useState([]);
    const [isEditing, setIsEditing] = useState(false);

    // Estado para manejar si estamos escribiendo una nueva categoria
    const [isNewCategoryMode, setIsNewCategoryMode] = useState(false);

    //  Estado para manejar la lista de categorías dinámicamente
    const [categoryList, setCategoryList] = useState(categories);

    const initialProductState = {
        name: '', price: 0, stock: 0, category: '', image: '',
        description: '', prescription: false,
        usageMode: '', instructions: '', recommendedFor: '', precautions: ''
    };

    const [currentProduct, setCurrentProduct] = useState(initialProductState);

    // Función para cargar la lista de categorías (desde el backend)
    const loadCategories = async () => {
        try {
            const fetchedCategories = await adminService.getCategories();
            setCategoryList(fetchedCategories);
        } catch (error) {
            console.error("Error al cargar categorías:", error);
            setCategoryList(categories);
        }
    };
    const loadProducts = async () => {
        const data = await productService.getAll();
        setProducts(data);
        useEffect(() => {
            loadProducts();
            loadCategories();
        }, []);
    };

    useEffect(() => { loadProducts(); }, []);

    const handleDelete = async (id) => {
        if (window.confirm("¿Seguro que quieres eliminar este producto?")) {
            await adminService.deleteProduct(id);
            alert("Producto eliminado (Simulado)");
            loadProducts();
        }
    };

    const handleSave = async (e) => {
        e.preventDefault();


        try {
            if (currentProduct.id) {
                await adminService.updateProduct(currentProduct.id, currentProduct);
                alert("✅ Producto actualizado con éxito.");
            } else {
                await adminService.createProduct(currentProduct);
                alert("✨ Producto creado con éxito.");
            }
        } catch (error) {
            console.error("Error en la operación:", error);
            alert(`❌ Error al guardar el producto: ${error.message || 'Error de red'}`);
            return; 
        }

        setIsEditing(false);
        setCurrentProduct(initialProductState);
        loadProducts(); 
    };

    const handleChange = (e) => {
        const { name, value, type, checked } = e.target;

        if (name === 'category') {
            if (value === 'new_category_mode') {
                setIsNewCategoryMode(true);
                setCurrentProduct({ ...currentProduct, category: '' });
                return;
            }
        }

        setCurrentProduct({
            ...currentProduct,
            [name]: type === 'checkbox' ? checked : value
        });
    };
    const handleCreateNewCategory = async (e) => {
        e.preventDefault(); 
        const newCategoryName = currentProduct.category.trim(); 

        if (!newCategoryName) return alert("El nombre de la categoría no puede estar vacío.");
        
        try {
            const newCategory = await adminService.createCategory({ name: newCategoryName }); 
            
            await loadCategories(); 
            
            setCurrentProduct(prev => ({ ...prev, category: newCategory.name || newCategoryName }));
            
            setIsNewCategoryMode(false);
            alert(`Categoría "${newCategoryName}" creada y seleccionada.`);

        } catch (error) {
            alert(`❌ Error al crear la categoría: ${error.message || 'Error de red'}`);
        }
    };

    // CARGAR IMAGEN LOCAL ---
    const handleImageUpload = (e) => {
        const file = e.target.files[0];
        if (file) {
            const localImageUrl = URL.createObjectURL(file);
            setCurrentProduct({ ...currentProduct, image: localImageUrl });
        }
    };

    return (
        <div>
            <div className="flex justify-between items-center mb-6">
                <h2 className="text-2xl font-bold text-gray-800">Inventario de Productos</h2>
                {!isEditing && (
                    <button
                        onClick={() => { setIsEditing(true); setCurrentProduct(initialProductState); setIsNewCategoryMode(false); }}
                        className="bg-[#0D99FF] text-white px-4 py-2 rounded-lg font-bold hover:bg-[#007acc] transition-colors"
                    >
                        + Nuevo Producto
                    </button>
                )}
            </div>

            {isEditing ? (
                <div className="bg-white p-8 rounded-2xl shadow-lg border border-gray-200 max-w-4xl mx-auto animate-fade-in-up">
                    <div className="flex justify-between items-center mb-6 border-b border-gray-100 pb-4">
                        <h3 className="text-xl font-bold text-gray-800">{currentProduct.id ? '✏️ Editar Producto' : '✨ Crear Nuevo Producto'}</h3>
                        <button onClick={() => setIsEditing(false)} className="text-gray-400 hover:text-red-500">✕ Cancelar</button>
                    </div>

                    <form onSubmit={handleSave} className="space-y-8">

                        <div className="space-y-4">
                            <h4 className="text-sm font-bold text-gray-400 uppercase tracking-wider">📦 Información General</h4>

                            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">

                                {/* Nombre */}
                                <div className="col-span-2">
                                    <label className="block text-sm font-medium text-gray-700 mb-1">Nombre</label>
                                    <input type="text" name="name" required className="w-full p-3 bg-gray-50 border border-gray-200 rounded-xl outline-none focus:border-[#0D99FF]" value={currentProduct.name} onChange={handleChange} />
                                </div>

                                {/* --- CATEGORÍA --- */}
                                <div>
                                <label className="block text-sm font-medium text-gray-700 mb-1">Categoría</label>
                                
                                {isNewCategoryMode ? (
                                    //CREAR NUEVA CATEGORÍA  ---
                                    <div className="flex space-x-2 mt-1">
                                        <input
                                            type="text"
                                            name="category"
                                            value={currentProduct.category}
                                            onChange={handleChange}
                                            placeholder="Escribe el nombre de la nueva categoría"
                                            className="flex-1 p-3 bg-blue-50 border border-blue-200 rounded-xl outline-none text-blue-800 font-medium"
                                            required
                                        />
                                        <button 
                                            type="button" 
                                            onClick={handleCreateNewCategory} 
                                            className="bg-green-500 text-white px-4 rounded-xl font-bold hover:bg-green-600 transition-colors"
                                        >
                                            Guardar
                                        </button>
                                        <button 
                                            type="button" 
                                            onClick={() => { setIsNewCategoryMode(false); setCurrentProduct(prev => ({...prev, category: ''})); }} 
                                            className="bg-gray-400 text-white px-4 rounded-xl font-bold hover:bg-gray-500 transition-colors"
                                        >
                                            Cancelar
                                        </button>
                                    </div>
                                ) : (
                                    <select
                                        name="category" 
                                        required 
                                        className="w-full p-3 bg-gray-50 border border-gray-200 rounded-xl outline-none focus:border-[#0D99FF]"
                                        value={currentProduct.category} 
                                        onChange={handleChange}
                                    >
                                        <option value="" disabled>Selecciona...</option>
                                        
                                        {/* Opción especial para activar el modo de creación */}
                                        <option value="new_category_mode" className="font-bold text-[#0D99FF] bg-gray-100">
                                            ➕ Crear Nueva Categoría
                                        </option>

                                        {/* Lista de categorías cargadas dinámicamente */}
                                        {categoryList.map(cat => (
                                            <option key={cat.id} value={cat.name}> 
                                                {cat.icon ? `${cat.icon} ` : ''} {cat.name}
                                            </option>
                                        ))}
                                    </select>
                                )}
                            </div>

                                {/* --- IMAGEN -- */}
                                <div>
                                    <label className="block text-sm font-medium text-gray-700 mb-1">Imagen del Producto</label>

                                    <div className="flex flex-col gap-3">
                                        {/* Disco Local */}
                                        <label className="flex items-center gap-2 px-4 py-2 bg-gray-100 rounded-xl cursor-pointer hover:bg-gray-200 transition-colors text-sm text-gray-600 font-bold border border-gray-300 border-dashed">
                                            <span>📂 Subir desde mi PC</span>
                                            <input type="file" accept="image/*" onChange={handleImageUpload} className="hidden" />
                                        </label>

                                        {/* Separador */}
                                        <div className="text-center text-xs text-gray-400">- O -</div>

                                        {/* URL Externa */}
                                        <input
                                            type="text" name="image" placeholder="Pegar URL de internet..." className="w-full p-2 bg-gray-50 border border-gray-200 rounded-lg text-sm outline-none"
                                            value={currentProduct.image} onChange={handleChange}
                                        />
                                    </div>

                                    {/* PREVISUALIZACIÓN */}
                                    {currentProduct.image && (
                                        <div className="mt-3 p-2 border border-gray-200 rounded-xl bg-gray-50 text-center">
                                            <p className="text-xs text-gray-400 mb-2">Vista Previa:</p>
                                            <img src={currentProduct.image} alt="Vista previa" className="h-24 mx-auto object-contain rounded-md" />
                                        </div>
                                    )}
                                </div>

                                {/* Precio y Stock */}
                                <div>
                                    <label className="block text-sm font-medium text-gray-700 mb-1">Precio ($)</label>
                                    <input type="number" name="price" required className="w-full p-3 bg-gray-50 border border-gray-200 rounded-xl outline-none" value={currentProduct.price} onChange={handleChange} />
                                </div>
                                <div>
                                    <label className="block text-sm font-medium text-gray-700 mb-1">Stock</label>
                                    <input type="number" name="stock" required className="w-full p-3 bg-gray-50 border border-gray-200 rounded-xl outline-none" value={currentProduct.stock} onChange={handleChange} />
                                </div>

                                {/* Checkbox Receta */}
                                <div className="col-span-2 bg-amber-50 p-4 rounded-xl border border-amber-100 flex items-center gap-3">
                                    <input type="checkbox" name="prescription" id="prescription" className="w-5 h-5 text-amber-500 rounded" checked={currentProduct.prescription} onChange={handleChange} />
                                    <label htmlFor="prescription" className="text-amber-800 font-bold cursor-pointer select-none">📄 Este producto requiere Receta Médica</label>
                                </div>

                                {/* Descripción Corta */}
                                <div className="col-span-2">
                                    <label className="block text-sm font-medium text-gray-700 mb-1">Descripción Corta</label>
                                    <textarea name="description" rows="2" className="w-full p-3 bg-gray-50 border border-gray-200 rounded-xl outline-none" value={currentProduct.description} onChange={handleChange}></textarea>
                                </div>
                            </div>
                        </div>

                        {/* SECCIÓN 2: INFO ADICIONAL (IGUAL QUE ANTES) */}
                        <div className="space-y-4 pt-6 border-t border-gray-100">
                            <h4 className="text-sm font-bold text-gray-400 uppercase tracking-wider flex items-center gap-2">ℹ️ Información Detallada (Opcional)</h4>
                            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                                <div className="bg-blue-50/50 p-6 rounded-2xl border border-blue-100 space-y-4">
                                    <h5 className="font-bold text-[#0D99FF] flex items-center gap-2">🧴 Modo de Uso</h5>
                                    <div><label className="block text-xs font-bold text-gray-500 uppercase mb-1">Vía de Administración</label><input type="text" name="usageMode" className="w-full p-2 bg-white border border-gray-200 rounded-lg text-sm" value={currentProduct.usageMode} onChange={handleChange} /></div>
                                    <div><label className="block text-xs font-bold text-gray-500 uppercase mb-1">Instrucciones</label><textarea name="instructions" rows="3" className="w-full p-2 bg-white border border-gray-200 rounded-lg text-sm" value={currentProduct.instructions} onChange={handleChange}></textarea></div>
                                </div>
                                <div className="bg-red-50/50 p-6 rounded-2xl border border-red-100 space-y-4">
                                    <h5 className="font-bold text-red-500 flex items-center gap-2">⚠️ Precauciones</h5>
                                    <div><label className="block text-xs font-bold text-gray-500 uppercase mb-1">Recomendado para</label><input type="text" name="recommendedFor" className="w-full p-2 bg-white border border-gray-200 rounded-lg text-sm" value={currentProduct.recommendedFor} onChange={handleChange} /></div>
                                    <div><label className="block text-xs font-bold text-gray-500 uppercase mb-1">Advertencias</label><textarea name="precautions" rows="3" className="w-full p-2 bg-white border border-gray-200 rounded-lg text-sm" value={currentProduct.precautions} onChange={handleChange}></textarea></div>
                                </div>
                            </div>
                        </div>

                        <div className="flex gap-4 justify-end pt-6 border-t border-gray-100">
                            <button type="button" onClick={() => setIsEditing(false)} className="px-6 py-3 text-gray-500 hover:bg-gray-100 rounded-xl font-bold transition-colors">Cancelar</button>
                            <button type="submit" className="px-8 py-3 bg-[#0D99FF] text-white rounded-xl font-bold hover:bg-[#007acc] shadow-lg shadow-blue-200 transition-transform active:scale-95">💾 Guardar Producto</button>
                        </div>

                    </form>
                </div>
            ) : (
                // --- TABLA  ---
                <div className="bg-white rounded-2xl shadow-sm border border-gray-200 overflow-hidden">
                    <table className="w-full text-left border-collapse">
                        <thead className="bg-gray-50 text-gray-500 uppercase text-xs">
                            <tr>
                                <th className="p-4">Producto</th>
                                <th className="p-4 hidden md:table-cell">Categoría</th>
                                <th className="p-4">Precio</th>
                                <th className="p-4">Stock</th>
                                <th className="p-4 text-right">Acciones</th>
                            </tr>
                        </thead>
                        <tbody className="divide-y divide-gray-100 text-sm">
                            {products.map(product => (
                                <tr key={product.id} className="hover:bg-gray-50 transition-colors">
                                    <td className="p-4 font-medium flex items-center gap-3">
                                        {product.image && <img src={product.image} alt="" className="w-8 h-8 rounded object-cover border border-gray-200" />}
                                        {product.name}
                                    </td>
                                    <td className="p-4 text-gray-500 hidden md:table-cell capitalize">{product.category}</td>
                                    <td className="p-4 font-bold text-gray-700">${product.price.toLocaleString()}</td>
                                    <td className="p-4">
                                        <span className={`px-2 py-1 rounded-full text-xs font-bold ${product.stock > 10 ? 'bg-green-100 text-green-700' : 'bg-red-100 text-red-700'}`}>
                                            {product.stock} u.
                                        </span>
                                    </td>
                                    <td className="p-4 text-right space-x-2">
                                        <button onClick={() => { setCurrentProduct(product); setIsEditing(true); }} className="text-[#0D99FF] hover:underline font-bold">Editar</button>
                                        <button onClick={() => handleDelete(product.id)} className="text-red-500 hover:underline">Borrar</button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            )}
        </div>
    );
};

export default AdminProducts;