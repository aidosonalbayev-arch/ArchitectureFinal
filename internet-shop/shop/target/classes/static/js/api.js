const API = '/api';

// ── AUTH ──
function getUser(){ const u=localStorage.getItem('user'); return u?JSON.parse(u):null; }
function saveUser(u){ localStorage.setItem('user',JSON.stringify(u)); }
function requireAuth(){
  const u=getUser();
  if(!u){ location.href='index.html'; return null; }
  const el=document.getElementById('nav-user');
  if(el) el.textContent=u.username;
  updateCartBadge(); return u;
}
function requireAdmin(){
  const u=requireAuth();
  if(u && u.role!=='ADMIN'){ location.href='products.html'; return null; }
  return u;
}
function logout(){ localStorage.removeItem('user'); localStorage.removeItem('cart'); location.href='index.html'; }

// ── CART ──
function getCart(){ const c=localStorage.getItem('cart'); return c?JSON.parse(c):[]; }
function saveCart(c){ localStorage.setItem('cart',JSON.stringify(c)); updateCartBadge(); }
function updateCartBadge(){
  const n=getCart().reduce((s,i)=>s+i.quantity,0);
  document.querySelectorAll('.cart-count').forEach(el=>el.textContent=n);
}
function addToCart(p){
  const cart=getCart();
  const ex=cart.find(i=>i.productId===p.id);
  if(ex) ex.quantity++; else cart.push({productId:p.id,name:p.name,price:p.price,quantity:1});
  saveCart(cart); toast('Added to cart ✓');
}

// ── HTTP ──
async function get(path){
  const r=await fetch(API+path);
  if(!r.ok) throw new Error(r.status);
  return r.json();
}
async function post(path,body){
  const r=await fetch(API+path,{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify(body)});
  if(!r.ok){ const e=await r.json().catch(()=>({})); throw new Error(e.message||r.status); }
  return r.json();
}
async function put(path,body){
  const r=await fetch(API+path,{method:'PUT',headers:{'Content-Type':'application/json'},body:JSON.stringify(body)});
  if(!r.ok){ const e=await r.json().catch(()=>({})); throw new Error(e.message||r.status); }
  return r.json();
}
async function del(path){
  const r=await fetch(API+path,{method:'DELETE'});
  if(!r.ok) throw new Error(r.status);
}
async function patch(path,body){
  const r=await fetch(API+path,{method:'PATCH',headers:{'Content-Type':'application/json'},body:JSON.stringify(body)});
  if(!r.ok){ const e=await r.json().catch(()=>({})); throw new Error(e.message||r.status); }
  return r.json();
}

// ── TOAST ──
function toast(msg,type=''){
  const t=document.createElement('div');
  t.style.cssText=`position:fixed;bottom:1.5rem;left:50%;transform:translateX(-50%) translateY(10px);
    background:${type==='err'?'#ef4444':'#6c63ff'};color:#fff;padding:.55rem 1.3rem;
    border-radius:30px;font-family:'DM Sans',sans-serif;font-size:.88rem;font-weight:600;
    z-index:9999;box-shadow:0 4px 20px rgba(0,0,0,.4);opacity:0;transition:all .3s;`;
  t.textContent=msg;
  document.body.appendChild(t);
  setTimeout(()=>{t.style.opacity='1';t.style.transform='translateX(-50%) translateY(0)';},10);
  setTimeout(()=>{t.style.opacity='0';setTimeout(()=>t.remove(),300);},2500);
}

// ── MODAL ──
function openModal(id){ document.getElementById(id)?.classList.remove('hidden'); document.getElementById('overlay')?.classList.remove('hidden'); }
function closeModal(id){ document.getElementById(id)?.classList.add('hidden'); document.getElementById('overlay')?.classList.add('hidden'); }
function closeAll(){ document.querySelectorAll('.modal').forEach(m=>m.classList.add('hidden')); document.getElementById('overlay')?.classList.add('hidden'); }

// ── FORMAT ──
function fmtPrice(p){ return '$'+Number(p).toLocaleString('en-US',{minimumFractionDigits:2,maximumFractionDigits:2}); }
function fmtDate(d){ return new Date(d).toLocaleDateString('en-US',{month:'short',day:'numeric',year:'numeric',hour:'2-digit',minute:'2-digit'}); }

// inject style for spin
const s=document.createElement('style');
s.textContent=`@keyframes spin{to{transform:rotate(360deg)}}`;
document.head.appendChild(s);
