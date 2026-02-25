// 1. Smooth Login Entry Animation (GSAP)
window.onload = () => {
    gsap.to(".login-box", { scale: 1, opacity: 1, duration: 0.8, ease: "back.out(1.7)" });
    gsap.from(".hero-text", { x: -100, opacity: 0, duration: 1, delay: 0.5 });
};

// 2. User Login Logic
function sendOTP() {
    const mobile = document.getElementById("mobileInput").value;
    if(mobile.length === 10) {
        document.getElementById("step1").style.display = "none";
        document.getElementById("step2").style.display = "block";
        gsap.from("#step2", { opacity: 0, y: 20, duration: 0.5 });
    } else {
        alert("Please enter a valid 10-digit number!");
    }
}

function verifyOTP() {
    const otp = document.getElementById("otpInput").value;
    if(otp.length > 0) {
        gsap.to(".overlay", { opacity: 0, duration: 0.5, onComplete: () => {
            document.getElementById("loginOverlay").style.display = "none";
        }});
    } else {
        alert("Enter OTP to enter the site.");
    }
}

// 3. Admin Login (Aapki ID aur Password)
function adminLogin() {
    const id = prompt("Enter Admin ID:");
    const pass = prompt("Enter Admin Password:");
    if(id === "Vivekshukla" && pass === "Vivek1234@") {
        alert("Welcome Boss! System Access Granted.");
        gsap.to(".overlay", { opacity: 0, duration: 0.5, onComplete: () => {
            document.getElementById("loginOverlay").style.display = "none";
        }});
    } else {
        alert("Access Denied!");
    }
}

// 4. Three.js 3D Setup (Best 3D Vibe)
const container = document.getElementById('canvas-container');
const scene = new THREE.Scene();
const camera = new THREE.PerspectiveCamera(75, container.clientWidth / container.clientHeight, 0.1, 1000);
const renderer = new THREE.WebGLRenderer({ alpha: true, antialias: true });

renderer.setSize(container.clientWidth, container.clientHeight);
container.appendChild(renderer.domElement);

// Ek 3D Object bana rahe hain jo rotate karega
const geometry = new THREE.IcosahedronGeometry(2.5, 1);
const material = new THREE.MeshStandardMaterial({ 
    color: 0x2d6a4f, 
    wireframe: false,
    roughness: 0.3,
    metalness: 0.2
});
const plant3D = new THREE.Mesh(geometry, material);
scene.add(plant3D);

// Lights add kar rahe hain 3D effect chamkane ke liye
const ambientLight = new THREE.AmbientLight(0xffffff, 0.7);
scene.add(ambientLight);
const dirLight = new THREE.DirectionalLight(0xffffff, 1);
dirLight.position.set(5, 5, 5);
scene.add(dirLight);

camera.position.z = 6;

// Animation Loop (Object ko lagatar ghumane ke liye)
function animate() {
    requestAnimationFrame(animate);
    plant3D.rotation.x += 0.003;
    plant3D.rotation.y += 0.005;
    renderer.render(scene, camera);
}
animate();

// Screen resize hone par 3D fix rahe
window.addEventListener('resize', () => {
    camera.aspect = container.clientWidth / container.clientHeight;
    camera.updateProjectionMatrix();
    renderer.setSize(container.clientWidth, container.clientHeight);
});
